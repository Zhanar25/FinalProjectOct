package kz.abuova.FinalProjectOct.services;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    boolean uploadPhoto(MultipartFile file,String email);

}
