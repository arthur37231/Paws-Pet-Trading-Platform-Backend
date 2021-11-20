package usyd.elec5619.group42.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/category", produces = APPLICATION_JSON_VALUE)
public class PetCategoryController {
}
