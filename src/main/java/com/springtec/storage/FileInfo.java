package com.springtec.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.nio.file.Path;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

   private String name;
   private Resource resource;

}
