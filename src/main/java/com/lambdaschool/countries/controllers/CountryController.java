package com.lambdaschool.countries.controllers;
import com.lambdaschool.countries.modules.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    CountryRepository ctrepos;
    private List<Country> findCountries(List<Country> myList, CheckCountry tester) {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList) {
            if (tester.test(c)) {
                tempList.add(c);
            }
        }
        return tempList;
    }


    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries() {

        List<Country> myList = new ArrayList<>();
        ctrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/'letter'
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByFirstLetter(@PathVariable char letter) {
        List<Country> myList = new ArrayList<>();
        ctrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> rtnList = findCountries(myList, c -> c.getName().charAt(0) == letter);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total <- this will print totalpop to console + return a Status OK
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> listTotalPopulation() {
        long totalPopulation = 0;
        List<Country> myList = new ArrayList<>();
        ctrepos.findAll().iterator().forEachRemaining(myList::add);
        for (Country c : myList) {
            totalPopulation = totalPopulation + c.getPopulation();
            System.out.println(c);
        }
        System.out.println("Total Population is: " + totalPopulation);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    // http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> listMinPopulation() {
        List<Country> myList = new ArrayList<>();
        ctrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }

    // http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> listMaxPopulation() {
        List<Country> myList = new ArrayList<>();
        ctrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> (int)(c2.getPopulation() - c1.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);
    }
}
