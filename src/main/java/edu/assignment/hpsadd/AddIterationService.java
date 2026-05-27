package edu.assignment.hpsadd;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AddIterationService {

    private static final Logger log = LoggerFactory.getLogger(AddIterationService.class);

    private final ChatClient chatClient;
    private final PromptTemplateLoader promptTemplateLoader;
    private final ConversationLogService conversationLogService;
    private final AssignmentProperties properties;
    private final String actualModel;

    public AddIterationService(
            ChatClient.Builder chatClientBuilder,
            PromptTemplateLoader promptTemplateLoader,
            ConversationLogService conversationLogService,
            AssignmentProperties properties,
            @Value("${spring.ai.dashscope.chat.options.model:${assignment.model}}") String actualModel
    ) {
        this.chatClient = chatClientBuilder.build();
        this.promptTemplateLoader = promptTemplateLoader;
        this.conversationLogService = conversationLogService;
        this.properties = properties;
        this.actualModel = actualModel;
    }

    public IterationResponse run(IterationRequest request) {
        String sessionId = UUID.randomUUID().toString();
        return runIteration(sessionId, request.iteration(), request.userPrompt());
    }

    public AssignmentRunResponse runAll(AssignmentRunRequest request) {
        String sessionId = UUID.randomUUID().toString();
        List<IterationResponse> responses = new ArrayList<>();
        for (int iteration = 1; iteration <= 4; iteration++) {
            responses.add(runIteration(sessionId, iteration, request.userPrompt()));
        }
        return new AssignmentRunResponse(sessionId, actualModel, responses.size(), responses);
    }

    private IterationResponse runIteration(String sessionId, int iteration, String userPrompt) {
        String systemPrompt = promptTemplateLoader.load("classpath:prompts/single-agent-system-prompt.txt");
        String promptTemplate = "iteration-" + iteration + ".txt";
        String iterationPrompt = promptTemplateLoader.load("classpath:prompts/" + promptTemplate);
        String fullUserPrompt = iterationPrompt + "\n\n[User Supplement]\n" + userPrompt;

        long startedAt = System.currentTimeMillis();
        try {
            String response = chatClient.prompt()
                    .system(systemPrompt)
                    .user(fullUserPrompt)
                    .call()
                    .content();
            long durationMs = System.currentTimeMillis() - startedAt;

            IterationLogRecord logRecord = conversationLogService.write(
                    sessionId,
                    iteration,
                    promptTemplate,
                    OffsetDateTime.now().toString(),
                    actualModel,
                    durationMs,
                    systemPrompt.length(),
                    fullUserPrompt.length(),
                    response.length(),
                    "unavailable",
                    systemPrompt,
                    fullUserPrompt,
                    response
            );

            return new IterationResponse(
                    sessionId,
                    iteration,
                    actualModel,
                    promptTemplate,
                    durationMs,
                    logRecord.tokenUsage(),
                    response,
                    logRecord.logFile()
            );
        }
        catch (RuntimeException ex) {
            long durationMs = System.currentTimeMillis() - startedAt;
            Throwable rootCause = rootCauseOf(ex);
            log.error(
                    "DashScope call failed: sessionId={}, iteration={}, model={}, durationMs={}, systemPromptChars={}, userPromptChars={}, rootCauseType={}, rootCauseMessage={}",
                    sessionId,
                    iteration,
                    actualModel,
                    durationMs,
                    systemPrompt.length(),
                    fullUserPrompt.length(),
                    rootCause.getClass().getName(),
                    rootCause.getMessage(),
                    ex
            );
            throw new IllegalStateException(
                    "DashScope request failed after " + durationMs + " ms for iteration " + iteration
                            + " using model " + actualModel + ". Root cause: "
                            + rootCause.getClass().getSimpleName() + ": " + rootCause.getMessage(),
                    ex
            );
        }
    }

    private static Throwable rootCauseOf(Throwable throwable) {
        Throwable current = throwable;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }
}
