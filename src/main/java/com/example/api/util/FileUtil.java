package com.example.api.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.lang.Nullable;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;

public class FileUtil extends FileCopyUtils {
	public static boolean delete(@Nullable File root) {
		if (ObjectUtils.isEmpty(root)) {
			return false;
		}
		try {
			return delete(root.toPath());
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean delete(@Nullable Path root) throws IOException {
		if (ObjectUtils.isEmpty(root)) {
			return false;
		}
		if (!Files.exists(root)) {
			return false;
		}

		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
		return true;
	}

	public static void copyNio(File src, File dest) throws IOException {
		if (ObjectUtils.isEmpty(src) || !src.exists()) {
			throw new IllegalArgumentException("source file must not be null or not exists");
		}
		if (ObjectUtils.isEmpty(dest) || !dest.canWrite()) {
			throw new IllegalArgumentException("destination file must not be null or not exists");
		}

		copyNio(src.toPath(), dest.toPath());
	}

	public static void copyNio(Path src, Path dest) throws IOException {
		if (ObjectUtils.isEmpty(src)) {
			throw new IllegalArgumentException("source path must not be null");
		}
		if (ObjectUtils.isEmpty(dest)) {
			throw new IllegalArgumentException("destination path must not be null");
		}

		BasicFileAttributes srcAttr = Files.readAttributes(src, BasicFileAttributes.class);

		if (srcAttr.isDirectory()) {
			Files.walkFileTree(src, new SimpleFileVisitor<>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					Files.createDirectories(dest.resolve(src.relativize(dir)));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
					return FileVisitResult.CONTINUE;
				}
			});
		} else if (srcAttr.isRegularFile()) {
			Files.copy(src, dest);
		} else {
			throw new IllegalArgumentException("source file must denote a directory or file");
		}
	}
}
