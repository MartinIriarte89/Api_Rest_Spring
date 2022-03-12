package com.martiniriarte.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.martiniriarte.error.AlmacenamientoArchivoNoEncontradoException;
import com.martiniriarte.error.AlmacenamientoException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServicioAlmacenamientoArchivoEnSitema implements ServicioAlmacenamiento {

	private final Path rootLocation;

	public ServicioAlmacenamientoArchivoEnSitema(@Value("${upload.root-location}") String path) {
		this.rootLocation = Paths.get(path);
	}

	@Override
	public String store(MultipartFile file) {
		if (file.isEmpty()) {
			throw new AlmacenamientoException("Failed to store empty file ");
		}

		String nombreOriginal = file.getOriginalFilename();
		log.info(nombreOriginal);
		String filename = "";
		if (nombreOriginal != null) {
			filename = StringUtils.cleanPath(nombreOriginal);
			log.info(filename);
		}

		String extension = StringUtils.getFilenameExtension(filename);
		String justFilename = filename.replace("." + extension, "");
		String storedFilename = System.currentTimeMillis() + "_" + justFilename + "." + extension;
		log.info(storedFilename);
		
		if (filename.contains("..")) {
			// This is a security check
			throw new AlmacenamientoException(
					"Cannot store file with relative path outside current directory " + filename);
		}

		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, this.rootLocation.resolve(storedFilename), StandardCopyOption.REPLACE_EXISTING);
			
			return storedFilename;
		} catch (IOException e) {
			throw new AlmacenamientoException("Failed to store file " + filename, e);
		}

	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new AlmacenamientoException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new AlmacenamientoArchivoNoEncontradoException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new AlmacenamientoArchivoNoEncontradoException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new AlmacenamientoException("Could not initialize storage", e);
		}
	}

	@Override
	public void delete(String filename) {
		String justFilename = StringUtils.getFilename(filename);
		try {
			Path file = load(justFilename);
			Files.deleteIfExists(file);
		} catch (IOException e) {
			throw new AlmacenamientoException("Error al eliminar un fichero", e);
		}

	}

}
