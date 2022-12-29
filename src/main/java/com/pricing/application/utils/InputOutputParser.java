package com.pricing.application.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.application.dto.CandidateInputDto;
import com.pricing.application.dto.CandidateOutputDto;
import com.pricing.application.exeptions.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class InputOutputParser {

    public static CandidateInputDto readCandidateInput(final String inputFilePath) throws ParseException {
        final StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(inputFilePath))) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            throw new ParseException("Exception occurred when opening input file");
        }

        final String inputFileStringContent = contentBuilder.toString();

        try {
            return new ObjectMapper().readValue(inputFileStringContent, CandidateInputDto.class);
        } catch (JsonProcessingException e) {
            throw new ParseException("Exception occurred when parsing input file");
        }
    }

    public static void writeCandidateOutput(final String outputFilepath, final CandidateOutputDto candidateOutput) throws IOException {
        OutputStream outputStream = new FileOutputStream(outputFilepath);
        new ObjectMapper().writeValue(outputStream, candidateOutput);
    }

}
