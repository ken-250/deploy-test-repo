package com.tabisketch.controller;

import com.tabisketch.service.IDeletePlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shara/{uuid}")
public class DeletePlanController {
    private final IDeletePlanService deletePlanService;

    public DeletePlanController(final IDeletePlanService deletePlanService) {
        this.deletePlanService = deletePlanService;
    }

    @DeleteMapping
    public String post(final @PathVariable String uuid) {
        this.deletePlanService.execute(uuid);
        return "redirect:/plan/list";
    }
}
