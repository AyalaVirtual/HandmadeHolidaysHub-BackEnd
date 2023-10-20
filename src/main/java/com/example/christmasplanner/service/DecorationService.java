package com.example.christmasplanner.service;

import com.example.christmasplanner.exception.InformationExistException;
import com.example.christmasplanner.exception.InformationNotFoundException;
import com.example.christmasplanner.model.Decoration;
import com.example.christmasplanner.repository.DecorationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class DecorationService {

    private DecorationRepository decorationRepository;


    @Autowired
    public void setDecorationRepository(DecorationRepository decorationRepository) {
        this.decorationRepository = decorationRepository;
    }


    /**
     * This is a GET request that checks to see if the list of decorations is empty before either throwing an InformationNotFoundException, or  returning the list of decorations
     *
     * @return a list of all decorations
     */
    public List<Decoration> getAllDecorations() {
        return decorationRepository.findAll();
    }


    /**
     * This is a GET request that checks to see if an individual decoration exists before either returning it, or throwing an InformationNotFoundException
     *
     * @param decorationId represents the id of the specific decoration the user is trying to get
     * @return decoration by id if it exists
     */
    public Optional<Decoration> getDecorationById(Long decorationId) {
        Optional<Decoration> decorationOptional = decorationRepository.findById(decorationId);

        if (decorationOptional.isPresent()) {
            return decorationOptional;
        } else {
            throw new InformationNotFoundException("decoration with id " + decorationId + " not found");
        }
    }

    public Decoration createDecoration(Decoration decorationObject) {
        Decoration decoration = decorationRepository.findByName(decorationObject.getName());

        if (decoration != null) {
            throw new InformationExistException("decoration with name " + decorationObject.getName() + " already exists");
        } else {
            return decorationRepository.save(decorationObject);
        }
    }

    public Optional<Decoration> updateDecoration(Long decorationId, Decoration decorationObject) {

        Optional<Decoration> decorationOptional = decorationRepository.findById(decorationId);

        if (decorationOptional.isPresent()) {
            decorationOptional.get().setName(decorationObject.getName());
            decorationOptional.get().setMaterials(decorationObject.getMaterials());
            decorationOptional.get().setDirections(decorationObject.getDirections());
            decorationRepository.save(decorationOptional.get());
            return decorationOptional;
        } else {
            throw new InformationNotFoundException("decoration with id " + decorationId + " not found");
        }
    }

    public Optional<Decoration> deleteDecoration(Long decorationId) {
        Optional<Decoration> decorationOptional = decorationRepository.findById(decorationId);

        if (decorationOptional.isPresent()) {
            decorationRepository.deleteById(decorationId);
            return decorationOptional;
        } else {
            throw new InformationNotFoundException("decoration with id " + decorationId + " not found");
        }
    }

}
