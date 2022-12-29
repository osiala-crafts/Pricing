package com.pricing.application.utils;

import com.pricing.application.dto.CandidateInputDto;
import com.pricing.application.exeptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

class InputOutputParserTest {

    @Test
    void when_inputFilePath_isOk_and_inputFileContent_isValid_then_return_validNotNullCandidateInputDto() throws ParseException, UnsupportedEncodingException {
        //given
        final String inputFileGoodPath = Objects.requireNonNull(getClass().getClassLoader().getResource("examples/good_candidates_input_json.txt")).getPath().substring(1);
        //when
        final CandidateInputDto candidateInputDto = InputOutputParser.readCandidateInput(URLDecoder.decode(inputFileGoodPath, StandardCharsets.UTF_8.toString()));
        //then
        Assertions.assertNotNull(candidateInputDto);
    }

    @Test
    void when_inputFilePath_isWrong_then_throwParseException_withFileOpeningErrorMessage() {
        //given
        final String inputFileWrongPath = "wrong/file/path";
        //when
        ParseException thrown = Assertions.assertThrows(ParseException.class, () -> InputOutputParser.readCandidateInput(inputFileWrongPath));
        //then
        Assertions.assertEquals("Exception occurred when opening input file", thrown.getMessage());
    }

    @Test
    void when_inputFilePath_isOk_and_havingWrongJsonFormat_then_throwParseException_withFileJsonParsingErrorMessage() {
        //given
        final String inputFileWrongJsonPath = Objects.requireNonNull(getClass().getClassLoader().getResource("examples/wrong_candidates_input_json.txt")).getPath().substring(1);
        //when
        ParseException thrown = Assertions.assertThrows(ParseException.class, () -> InputOutputParser.readCandidateInput(URLDecoder.decode(inputFileWrongJsonPath, StandardCharsets.UTF_8.toString())));
        //then
        Assertions.assertEquals("Exception occurred when parsing input file", thrown.getMessage());
    }
}