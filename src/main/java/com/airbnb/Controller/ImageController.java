package com.airbnb.Controller;


import com.airbnb.Entity.Property;
import com.airbnb.Entity.PropertyUser;
import com.airbnb.Service.BucketService;
import com.airbnb.Service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @PathVariable Long propertyId,
                                        @AuthenticationPrincipal PropertyUser user
    ) {

        imageService.saveImage(file, bucketName, propertyId, user);
        return new ResponseEntity<>("image is saved", HttpStatus.OK);
    }

    @DeleteMapping(path = "/upload/file/{bucketName}/property/{propertyId}/{photokey}/{imageId}")
    public ResponseEntity<?> deleteFile(@PathVariable String photokey,
                                        @PathVariable String bucketName,
                                        @PathVariable Long propertyId,
                                        @PathVariable Long imageId)
    {


        String s = imageService.deletePhoto(photokey, bucketName, propertyId, imageId);
        return new ResponseEntity<>(s,HttpStatus.OK);

    }
}



