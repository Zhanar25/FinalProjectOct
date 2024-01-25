package kz.abuova.FinalProjectOct.services.impl;

import kz.abuova.FinalProjectOct.antities.Item;
import kz.abuova.FinalProjectOct.antities.Photo;
import kz.abuova.FinalProjectOct.antities.Users;
import kz.abuova.FinalProjectOct.repositories.ItemRepository;
import kz.abuova.FinalProjectOct.repositories.PhotoRepository;
import kz.abuova.FinalProjectOct.repositories.UsersRepository;
import kz.abuova.FinalProjectOct.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Override
    public boolean uploadPhoto(MultipartFile file, String email) {
        try {
            Users user=usersRepository.findAllByEmail(email);
            Photo photo=Photo.builder()
                    .name(email+user.getPhotos().size()+".jpg")
                    .build();
            byte bytes[]= file.getBytes();
            Path path= Paths.get("build/resources/main/static/"+email+user.getPhotos().size()+".jpg");
            Files.write(path,bytes);
            photoRepository.save(photo);
            user.getPhotos().add(photo);
            usersRepository.save(user);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
