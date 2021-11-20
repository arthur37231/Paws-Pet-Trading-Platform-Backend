package usyd.elec5619.group42.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usyd.elec5619.group42.backend.pojo.Picture;
import usyd.elec5619.group42.backend.repository.PictureRepository;

import javax.annotation.Resource;
import java.io.*;
import java.util.Objects;
@CrossOrigin
@RestController
@RequestMapping(path = "/picture")

public class PictureController {
    @Autowired
    PictureRepository pictureRepository;
    private String location = "frontend/src/assets/images/Picture";
    @Transactional
    @PostMapping("/userImageUpload")
    public Picture userImageUpload(@RequestParam(value = "file") MultipartFile file) {

        long currentTime = System.currentTimeMillis();
        String fileName = String.valueOf(currentTime).concat(file.getOriginalFilename());
        System.out.println(fileName);
        String filePath = location;
        if (!file.isEmpty()) {
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filePath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                Picture picture = pictureRepository.save(new Picture(null,fileName));
                return picture;
            } catch (FileNotFoundException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }

    }

}
