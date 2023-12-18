package com.springtec.services.impl;

import com.springtec.exceptions.ElementNotExistInDBException;
import com.springtec.exceptions.InvalidArgumentException;
import com.springtec.models.dto.ITypeUserDTO;
import com.springtec.models.dto.ImageUploadDto;
import com.springtec.models.dto.TechnicalDto;
import com.springtec.models.dto.UserDto;
import com.springtec.models.entity.ImageUser;
import com.springtec.models.entity.User;
import com.springtec.models.payload.UserRequest;
import com.springtec.models.repositories.ImageUserRepository;
import com.springtec.models.repositories.RoleRepository;
import com.springtec.models.repositories.UserRepository;
import com.springtec.services.IUserService;
import com.springtec.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImplService implements IUserService {

   private final UserRepository userRepository;
   private final StorageService storageService;
   private final ImageUserRepository imageUserRepository;
   private final TechnicalImplService technicalService;
   private final ClientImplService clientService;
   private final PasswordEncoder passwordEncoder;

   @Override
   public List<UserDto> findAll() {
      return null;
   }

   @Override
   public ImageUploadDto getImageByUserId(Integer id) {
      return null;
   }

   @Override
   public ITypeUserDTO findById(Integer id) throws ElementNotExistInDBException {
      User user = userRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("No existe el usuario con id -> "+ id));
      return getTypeUser(user);
   }

   private ITypeUserDTO getTypeUser(User user) {
      return
          switch (user.getRole().getName().toUpperCase()) {
            case "TECHNICAL" -> technicalService.findByUser(user);
            case "CLIENT" -> clientService.findByUser(user);
            default ->
               // todo CONTROLAR AQUI CON UN ERROR
               null;
         };
   }



   @Override
   public void update(UserRequest user, Integer id) throws Exception {
      User userBeforeUpdate = userRepository.findById(id)
          .orElseThrow(() -> new ElementNotExistInDBException("No existe el usuario con id -> "+ id));

      // Si no se envio ninguna imagen
      if (user.getFile() == null) {
         userBeforeUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(userBeforeUpdate);
         return;
      }

      if (user.getFile().getSize() > storageService.MAX_SIZE)
         throw new InvalidArgumentException("El archivo '"+user.getFile().getOriginalFilename()+"' pesa "+user.getFile().getSize()+" Bytes, no puede exceder los "+storageService.MAX_SIZE+" Bytes");

      String originalFileName = user.getFile().getOriginalFilename();
      // Guardamos la imagen en el storage
      String newFileEncryteName = storageService.store(user.getFile());

      // Si aún no se registro una imagen
      if (!imageUserRepository.existsByUserId(user.getId())){
         // Guarar la imagen
         imageUserRepository.save(
             ImageUser.builder()
                 .userId(user.getId())
                 .originalName( storageService.getFileName(originalFileName) )
                 .extensionName( storageService.getFileExtension(originalFileName) )
                 .contentType( user.getFile().getContentType() )
                 .fakeName( storageService.getFileName( newFileEncryteName ))
                 .fakeExtensionName( storageService.getFileExtension( newFileEncryteName ) )
                 .build()
         );
         return;
      }
      // En caso ya exista un registro, lo buscamos para actualizar
      ImageUser imageSaved = imageUserRepository.findByUserId(userBeforeUpdate.getId());
      String fileEncrypteName = imageSaved.getFakeExtensionName() +"."+ imageSaved.getFakeExtensionName();
      // Eliminamos la imagen existente
      storageService.delete(fileEncrypteName);

      imageSaved.setUserId(user.getId());
      imageSaved.setOriginalName(storageService.getFileName(originalFileName));
      imageSaved.setExtensionName(storageService.getFileExtension(originalFileName));
      imageSaved.setContentType(user.getFile().getContentType());
      imageSaved.setFakeName(storageService.getFileName(newFileEncryteName));
      imageSaved.setFakeExtensionName(storageService.getFileExtension(newFileEncryteName));
      imageUserRepository.save(imageSaved);
   }

   @Override
   public UserDto deleteById(Integer id) {
      return null;
   }

   @Override
   public boolean existsById(Integer id) {
      return false;
   }
}
