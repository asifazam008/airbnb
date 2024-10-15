package com.airbnb.Service;

import com.airbnb.Entity.Images;
import com.airbnb.Entity.Property;
import com.airbnb.Entity.PropertyUser;
import com.airbnb.repository.ImagesRepository;
import com.airbnb.repository.PropertyRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    
    private ImagesRepository imagesRepository;
    
    private BucketService bucketService;
    
    private PropertyRepository propertyRepository;

    @Autowired
    private AmazonS3 amazonS3;

    public ImageService(ImagesRepository imagesRepository, BucketService bucketService, PropertyRepository propertyRepository) {
        this.imagesRepository = imagesRepository;
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
    }

    public void saveImage(MultipartFile file, String bucketName, Long propertyId, PropertyUser user) {
        String url = bucketService.uploadFile(file, bucketName);
        Property  property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new RuntimeException("property not found")
        );
        Images img= new Images();
        img.setImageUrl(url);
        img.setProperty(property);
        img.setPropertyUser(user);
        Images save = imagesRepository.save(img);

    }

    public String deletePhoto(String photokey, String bucketName, Long propertyId, long imageId) {

      amazonS3.deleteObject(new DeleteObjectRequest(bucketName,photokey));
        Images image = imagesRepository.findByIdAndPropertyId(imageId, propertyId);
        if(image!=null){
            imagesRepository.deleteById(imageId);
        }
        else{
            throw new RuntimeException("image not found");
        }
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName,photokey));
            return "image is deleted";
        }catch (RuntimeException e){
            return null;
        }

    }
}
