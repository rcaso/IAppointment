package com.shavatech.presentation.management.doctor;

import com.shavatech.domain.management.boundary.DoctorRecord;
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
@Named("doctorView")
public class DoctorView implements Serializable {

    @Inject
    @RestClient
    DoctorService doctorService;

    private List<DoctorDTO> doctors;

    private DoctorDTO selectedDoctor;

    @PostConstruct
    public void init(){
        doctors = doctorService.getAll().stream().map(d->new DoctorDTO(d.id(),d.name(),d.lastName())).toList();
    }

    public void onRowEdit(RowEditEvent<DoctorDTO> event) {
        var doctorRecord = new DoctorRecord(event.getObject().getId(),event.getObject().getName(),event.getObject().getLastName());
        doctorService.updateDoctor(doctorRecord);
    //    doctors = new ArrayList<>();
    //    init();
        FacesMessage msg = new FacesMessage("Doctor Editado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<DoctorDTO> event) {
        FacesMessage msg = new FacesMessage("Edición Cancelado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveDoctor(){
        if(selectedDoctor != null){
            var doctorRecord = new DoctorRecord(null,selectedDoctor.getName(),selectedDoctor.getLastName());
            doctorService.createDoctor(doctorRecord);
            init();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Doctor Agregado"));
            PrimeFaces.current().executeScript("PF('manageDoctorDialog').hide()");
            PrimeFaces.current().ajax().update("form:msgs", "form:doctors");
        }
    }

    public void openNew(){
        selectedDoctor = new DoctorDTO();
    }

    public List<DoctorDTO> getDoctors() {
        return doctors;
    }

    public DoctorDTO getSelectedDoctor() {
        return selectedDoctor;
    }

    public void setSelectedDoctor(DoctorDTO selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }
}
