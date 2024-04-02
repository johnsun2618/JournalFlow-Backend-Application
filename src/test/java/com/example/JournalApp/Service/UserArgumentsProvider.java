package com.example.JournalApp.Service;

import com.example.JournalApp.Entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("username1").password("john").build()),
                Arguments.of(User.builder().userName("username2").password("sun").build())
        );
    }
}
