package com.example.apiuser.Contracts;

public interface UseCase<I, O> {
    O execute(I input);
}