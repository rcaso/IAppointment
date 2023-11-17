package com.shavatech.presentation.management.teacher;

import com.shavatech.domain.management.boundary.TeacherRecord;
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
public class TeacherView implements Serializable {

    @Inject
    @RestClient
    TeacherService teacherService;

    private List<TeacherDTO> teachers;

    private TeacherDTO selectedTeacher;

    @PostConstruct
    public void init(){
        teachers = teacherService.getAll().stream().map(d->new TeacherDTO(d.id(),d.name(),d.lastName())).toList();
    }

    public void onRowEdit(RowEditEvent<TeacherDTO> event) {
        var teacherRecord = new TeacherRecord(event.getObject().getId(),event.getObject().getName(),event.getObject().getLastName());
        teacherService.updateTeacher(teacherRecord);
        FacesMessage msg = new FacesMessage("Profesor Editado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<TeacherDTO> event) {
        FacesMessage msg = new FacesMessage("Edición Cancelado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveTeacher(){
        if(selectedTeacher != null){
            var teacherRecord = new TeacherRecord(null,selectedTeacher.getName(),selectedTeacher.getLastName());
            teacherService.createTeacher(teacherRecord);
            init();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Profesor Agregado"));
            PrimeFaces.current().executeScript("PF('manageTeacherDialog').hide()");
            PrimeFaces.current().ajax().update("form:msgs", "form:teachers");
        }
    }

    public void openNew(){
        selectedTeacher = new TeacherDTO();
    }


    public List<TeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherDTO> teachers) {
        this.teachers = teachers;
    }

    public TeacherDTO getSelectedTeacher() {
        return selectedTeacher;
    }

    public void setSelectedTeacher(TeacherDTO selectedTeacher) {
        this.selectedTeacher = selectedTeacher;
    }
}
