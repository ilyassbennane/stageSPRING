package com.ramadan.api.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramadan.api.entity.file.File;

public interface FileRepository extends JpaRepository<File, Long> {
}