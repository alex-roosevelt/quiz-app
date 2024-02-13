package com.example.quiz;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.quiz.model.QuizQuestion;
import com.example.quiz.repository.QuizQuestionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuizApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private QuizQuestionRepository questionRepository;

  @BeforeEach
  public void setUp() {
    questionRepository.deleteAll();
  }

  @Test
  public void testCheckAnswerWithCorrectAnswer() throws Exception {
    QuizQuestion question = new QuizQuestion();
    question.setQuestion("When was the \"The Beatles\" music\n"
        + "band formed? 1. In 1960's, 2. In\n"
        + "1970's.?");
    question.setCorrectAnswer("1");
    question = questionRepository.save(question);

    mockMvc.perform(post("/api/quiz/checkAnswer")
            .param("questionId", question.getId().toString())
            .param("userAnswer", "1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  public void testCheckAnswerWithIncorrectAnswer() throws Exception {
    QuizQuestion question = new QuizQuestion();
    question.setQuestion("When was the \"The Beatles\" music\n"
        + "band formed? 1. In 1960's, 2. In\n"
        + "1970's.?");
    question.setCorrectAnswer("1");
    question = questionRepository.save(question);

    mockMvc.perform(post("/api/quiz/checkAnswer")
            .param("questionId", question.getId().toString())
            .param("userAnswer", "2")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  public void testCheckAnswerWithNonExistentQuestion() throws Exception {
    mockMvc.perform(post("/api/quiz/checkAnswer")
            .param("questionId", "123")
            .param("userAnswer", "0")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdateQuestionWithEmptyFields() throws Exception {
    QuizQuestion question = new QuizQuestion();
    question.setQuestion("When was the \"The Beatles\" music\n"
        + "band formed? 1. In 1960's, 2. In\n"
        + "1970's.?");
    question.setCorrectAnswer("1");
    question = questionRepository.save(question);

    QuizQuestion updatedQuestion = new QuizQuestion();
    updatedQuestion.setId(question.getId());
    updatedQuestion.setQuestion("");
    updatedQuestion.setCorrectAnswer("");

    mockMvc.perform(put("/api/quiz/{id}", question.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedQuestion)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetAllQuestions() throws Exception {
    mockMvc.perform(get("/api/questions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }
}