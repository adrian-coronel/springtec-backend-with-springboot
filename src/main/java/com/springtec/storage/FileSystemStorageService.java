package com.springtec.storage;

import com.springtec.exceptions.StorageException;
import com.springtec.exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {

			// Verifica si la ubicación esta vacio
        if(properties.getLocation().trim().isEmpty()){// Trim() elimina los espacio en blanco al inicio y final
            throw new StorageException("La ubicación de carga del archivo no puede estar vacía.");
        }
		// Establece la ubicacion base donde estarán los archivos
		this.rootLocation = Paths.get(properties.getLocation());
	}


	@Override
	public String store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			String originalFileNameEncrypted = FileEncryptor.getEncryptFileName();
			// Resuelve la ruta del archivo de destino
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(originalFileNameEncrypted))
				//  La normalización elimina cualquier parte redundante o puntos extra en la ruta
				.normalize()
				// Convierte la ruta resultante en una ruta absoluta.
				.toAbsolutePath();

			// Realiza una verificación de seguridad: asegura que el archivo se almacene dentro del directorio actual
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// Esto es una verificación de seguridad
				throw new StorageException(
					"Cannot store file outside current directory.");
			}

			// Abre un flujo de entrada desde el archivo MultipartFile
			try (InputStream inputStream = file.getInputStream()) {
				// Copia el contenido del flujo de entrada al archivo de destino
				Files.copy(
					inputStream,
					destinationFile,
					StandardCopyOption.REPLACE_EXISTING // REEMPLAZA EL ARCHIVO SI YA EXISTE
				);
			}
			//Retornamos el nombre del archivo encriptado
			return originalFileNameEncrypted;
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}


	@Override
	public Stream<Path> loadAll() {
		try {
			// Se utiliza walk() para obtener un Stream<Path> que represetna
			// todos los archivos en "rootLocation"
			return Files.walk(this.rootLocation, 1) // maxDepth -> Niveles de directorios a explorar
					.filter(path -> !path.equals(this.rootLocation)) // Evita que se incluya la RAIZ
					.map(this.rootLocation::relativize); // La relativización se realiza para proporcionar rutas más concisas y legibles.
		} catch (IOException e) {
			throw new StorageException("No se pudieron leer los archivos almacenados", e);
		}

	}

	/**
	 * Retorna la ruta (Path) asociada al archivo(almacenado) con el nombre especificado
	 * */
	@Override
	public Path load(String filename) {
		//Une el rootLocation con el filename que representa al archivo almacenado
		return rootLocation.resolve(filename);
	}

	/**
	 *  Carga el archivo con el nombre proporcionado como un recurso (Resource
	 * */
	@Override
	public Resource loadAsResource(String filename) {
		try {
			// Se obtiene la ruta del archivo
			Path file = load(filename);
			// Creamos un recurso BASADO en una URL
			Resource resource = new UrlResource(file.toUri());
			// Se verifica si el recurso existe y es legible.
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("No se pudo leer el archivo: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("No se pudo leer el archivo: " + filename, e);
		}
	}

	@Override
	public byte[] loadAsDecryptedFile(String encryptedFileName,String originalFileName) throws IOException {
		// Renombramos el recurso existente
		renameResource(encryptedFileName, originalFileName);

		// Cargamos le recurso con actualizado
		Path imagePath = load(originalFileName);
		byte[] imageBytes = Files.readAllBytes(imagePath);

		// Volvemos a encryptar el recurso
		renameResource(originalFileName, encryptedFileName);

		// Retornamos el recurso con el nombre y la extensión correcta
		return imageBytes;
	}

	/**
	 * Elimina todos los archivos y directorios almacenados
	 * en la ubicación raíz (rootLocation
	 * */
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public String getFileName(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex != -1) {
			return fileName.substring(0, lastDotIndex);
		}
		return fileName; // No hay punto, devolver el nombre completo
	}

	@Override
	public String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex != -1) {
			return fileName.substring(lastDotIndex + 1);
		}
		return ""; // No hay punto, devolver una cadena vacía
	}

	/**
	 * Se encarga de inicializar el servicio de almacenamiento.
	 * Crea un directorio en la ruta especificada
	 * */
	@Override
	public void init() {
		try {
			//Si el directorio ya existe, no ocurrirá nada.
			//Si el directorio no existe, se creará.
			if (!Files.exists(rootLocation))
				Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	private void renameResource(String fileName, String newFileName) throws IOException {
		Path fileNameSaved = load(fileName);

		// Construimos la nueva ruta con el nombre original y la extensión original
		Path renamedFilePath = fileNameSaved.resolveSibling(newFileName);

		// Renombramos el archivo encriptado con el nombre y la extensión originales
		Files.move(fileNameSaved, renamedFilePath, StandardCopyOption.REPLACE_EXISTING);
	}

}
