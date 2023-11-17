package com.shavatech.presentation.management.therapist;

import com.shavatech.domain.management.boundary.DoctorRecord;
import com.shavatech.domain.management.boundary.TherapistRecord;
import com.shavatech.presentation.management.doctor.DoctorDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class TherapistView implements Serializable {

    @Inject
    @RestClient
    TherapistService therapistService;

    private List<TherapistDTO> therapists;

    private TherapistDTO selectedTherapist;

    @PostConstruct
    public void init(){
        therapists = therapistService.getAll().stream().map(t-> new TherapistDTO(t.id(),t.name(),t.lastName())).toList();
    }

    public void onRowEdit(RowEditEvent<TherapistDTO> event) {
        var therapistRecord = new TherapistRecord(event.getObject().getId(),event.getObject().getName(),event.getObject().getLastName());
        therapistService.updateTherapist(therapistRecord);
        FacesMessage msg = new FacesMessage("Terapista Editado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<TherapistDTO> event) {
        FacesMessage msg = new FacesMessage("Edición Cancelado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveTherapist(){
        if(selectedTherapist != null){
            var therapistRecord = new TherapistRecord(null,selectedTherapist.getName(),selectedTherapist.getLastName());
            therapistService.createTherapist(therapistRecord);
            init();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Terapista Agregado"));
            PrimeFaces.current().executeScript("PF('manageTherapistDialog').hide()");
            PrimeFaces.current().ajax().update("form:msgs", "form:therapists");
        }
    }

    public void openNew(){
        selectedTherapist = new TherapistDTO();
    }

    public List<TherapistDTO> getTherapists() {
        return therapists;
    }

    public void setTherapists(List<TherapistDTO> therapists) {
        this.therapists = therapists;
    }

    public TherapistDTO getSelectedTherapist() {
        return selectedTherapist;
    }

    public void setSelectedTherapist(TherapistDTO selectedTherapist) {
        this.selectedTherapist = selectedTherapist;
    }
}
