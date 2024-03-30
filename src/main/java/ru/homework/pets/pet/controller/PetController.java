package ru.homework.pets.pet.controller;

import com.google.gson.Gson;
import ru.homework.pets.pet.dto.NewPetDto;
import ru.homework.pets.pet.dto.PetDto;
import ru.homework.pets.pet.dto.UpdatePetDto;
import ru.homework.pets.pet.service.PetService;
import ru.homework.pets.pet.service.PetServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "PetServlet", value = "/pets")
public class PetController extends HttpServlet {

    PetService petService = new PetServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        NewPetDto newPetDto = new Gson().fromJson(request.getReader(), NewPetDto.class);

        try {
            petService.addPet(newPetDto);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("Record saved successfully!");
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Sorry! unable to save record");
            e.printStackTrace();
        }
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String petId = request.getParameter("id");

        int id = Integer.parseInt(petId);

        try {
            petService.deletePet(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            out.print("Record deleted successfully!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        if (request.getParameter("id") == null) {
            List<PetDto> list = null;
            try {
                list = petService.getAllPets();
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (list != null) {
                out.print(list.stream().map(petDto -> new Gson().toJson(petDto)).collect(Collectors.toList()));
            }
        } else {
            PetDto petDto = null;
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                petDto = petService.getPetById(id);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.print(new Gson().toJson(petDto));
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String petId = request.getParameter("id");
        int id = Integer.parseInt(petId);

        UpdatePetDto updatePetDto = new Gson().fromJson(request.getReader(), UpdatePetDto.class);

        try {
            PetDto oldPetDto = petService.getPetById(id);
            if (updatePetDto.getType() == null) {
                updatePetDto.setType(oldPetDto.getType());
            }
            if (updatePetDto.getName() == null) {
                updatePetDto.setName(oldPetDto.getName());
            }
            if (updatePetDto.getAge() == 0) {
                updatePetDto.setAge(oldPetDto.getAge());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            petService.updatePet(id, updatePetDto);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("Record updated successfully!");
        }
        catch (Exception e) {
            out.println("Sorry! unable to update record");
            e.printStackTrace();
        }
        out.close();
    }
}
