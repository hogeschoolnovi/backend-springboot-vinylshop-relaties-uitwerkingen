package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.entities.PublisherEntity;
import nl.novi.vinylshop.mappers.entity.PublisherEntityMapper;
import nl.novi.vinylshop.models.PublisherModel;
import nl.novi.vinylshop.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService{

    private final PublisherRepository publisherRepository;
    private final PublisherEntityMapper publisheEntityMapper;

    public PublisherService(PublisherRepository publisherRepository, PublisherEntityMapper publisherEntityMapper) {
        this.publisherRepository = publisherRepository;
        this.publisheEntityMapper = publisherEntityMapper;
    }

    //    Deze functie maakt gebruik van de Java Stream API. Dat is een korte manier om een for-loop te schrijven, maar het biedt ook nog andere functionaliteiten. Dit wordt niet in de stof behandeld, maar probeer eens te ontcijferen wat deze functie doet. Je zult zien dat het best intuïtief is.
    public List<PublisherModel> findAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(publisheEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public PublisherModel findPublisherById(Long id) throws EntityNotFoundException {
        PublisherEntity publisherEntity = getPublisherEntity(id);
        return publisheEntityMapper.fromEntity(publisherEntity);
    }


    public PublisherModel createPublisher(PublisherModel publisherModel) {
        PublisherEntity publisherEntity = publisheEntityMapper.toEntity(publisherModel);
        publisherEntity = publisherRepository.save(publisherEntity);
        return publisheEntityMapper.fromEntity(publisherEntity);
    }

    public PublisherModel updatePublisher(Long id, PublisherModel publisherModel) throws EntityNotFoundException {
        PublisherEntity existingPublisherEntity = getPublisherEntity(id);

        existingPublisherEntity.setName(publisherModel.getName());
        existingPublisherEntity.setAddress(publisherModel.getAddress());
        existingPublisherEntity.setContactDetails(publisherModel.getContactDetails());

        existingPublisherEntity = publisherRepository.save(existingPublisherEntity);
        return publisheEntityMapper.fromEntity(existingPublisherEntity);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }

    //    Deze helper methode haalt de Entity uit de Repository en valideert het. Deze actie werd op meerdere plekken gedaan, daarom is er een helper methode voor gemaakt.
    private PublisherEntity getPublisherEntity(Long id) {
        PublisherEntity publisherEntity = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher " + id +" not found"));
        return publisherEntity;
    }

}
