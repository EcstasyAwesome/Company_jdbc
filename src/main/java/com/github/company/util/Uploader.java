package com.github.company.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Part;

public interface Uploader {
    @Nullable String upload(@NotNull Part file) throws IllegalStateException;
    void rollBack();
}