package ru.homework.pets.owner.controller;

import com.google.gson.Gson;
import ru.homework.pets.owner.dto.NewOwnerDto;
import ru.homework.pets.owner.dto.OwnerDto;
import ru.homework.pets.owner.dto.UpdateOwnerDto;
import ru.homework.pets.owner.service.OwnerService;
import ru.homework.pets.owner.service.OwnerServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "OwnerServlet", value = "/owners")
public class OwnerController extends HttpServlet {

    OwnerService ownerService = new OwnerServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        NewOwnerDto newOwnerDto = new Gson().fromJson(request.getReader(), NewOwnerDto.class);

        try {
            ownerService.addOwner(newOwnerDto);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("Record saved successfully!");
        } catch (Exception e) {
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

        String ownerId = request.getParameter("id");

        int id = Integer.parseInt(ownerId);

        try {
            ownerService.deleteOwner(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            out.print("Record deleted successfully!");
        } catch (Exception e) {
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
            List<OwnerDto> list = null;
            try {
                list = ownerService.getAllOwners();
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (list != null) {
                out.print(list.stream().map(ownerDto -> new Gson().toJson(ownerDto)).collect(Collectors.toList()));
            }
        } else {
            OwnerDto ownerDto = null;
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                ownerDto = ownerService.getOwnerById(id);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.print(new Gson().toJson(ownerDto));
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String ownerId = request.getParameter("id");
        int id = Integer.parseInt(ownerId);

        UpdateOwnerDto updateOwnerDto = new Gson().fromJson(request.getReader(), UpdateOwnerDto.class);

        try {
            OwnerDto oldOwnerDto = ownerService.getOwnerById(id);
            if (updateOwnerDto.getName() == null) {
                updateOwnerDto.setName(oldOwnerDto.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ownerService.updateOwner(id, updateOwnerDto);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("Record updated successfully!");
        } catch (Exception e) {
            out.println("Sorry! unable to update record");
            e.printStackTrace();
        }
        out.close();
    }
}
