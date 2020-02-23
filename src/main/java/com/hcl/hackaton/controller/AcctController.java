package com.hcl.hackaton.controller;

import com.hcl.hackaton.exception.ResourceNotFoundException;
import com.hcl.hackaton.model.Acct;
import com.hcl.hackaton.repository.AcctRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Acct controller.
 *
 * @author Denhaag team
 */
@RestController
@RequestMapping("/api/v1")
public class AcctController {

  @Autowired
  private AcctRepository AcctRepository;

  /**
   * Get all Accts list.
   *
   * @return the list
   */
  @GetMapping("/Accts")
  public List<Acct> getAllAccts() {
    return AcctRepository.findAll();
  }

  /**
   * Gets Accts by id.
   *
   * @param AcctId the Acct id
   * @return the Accts by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/Accts/{id}")
  public ResponseEntity<Acct> getAcctsById(@PathVariable(value = "id") Long AcctId)
      throws ResourceNotFoundException {
    Acct Acct =
        AcctRepository
            .findById(AcctId)
            .orElseThrow(() -> new ResourceNotFoundException("Acct not found on :: " + AcctId));
    return ResponseEntity.ok().body(Acct);
  }

  /**
   * Create Acct Acct.
   *
   * @param Acct the Acct
   * @return the Acct
   */
  @PostMapping("/Accts")
  public Acct createAcct(@Valid @RequestBody Acct Acct) {
    return AcctRepository.save(Acct);
  }

  /**
   * Update Acct response entity.
   *
   * @param AcctId the Acct id
   * @param AcctDetails the Acct details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/Accts/{id}")
  public ResponseEntity<Acct> updateAcct(
      @PathVariable(value = "id") Long AcctId, @Valid @RequestBody Acct AcctDetails)
      throws ResourceNotFoundException {

    Acct Acct =
        AcctRepository
            .findById(AcctId)
            .orElseThrow(() -> new ResourceNotFoundException("Acct not found on :: " + AcctId));

    Acct.setEmail(AcctDetails.getEmail());
    Acct.setLastName(AcctDetails.getLastName());
    Acct.setFirstName(AcctDetails.getFirstName());
    Acct.setUpdatedAt(new Date());
    final Acct updatedAcct = AcctRepository.save(Acct);
    return ResponseEntity.ok(updatedAcct);
  }

  /**
   * Delete Acct map.
   *
   * @param AcctId the Acct id
   * @return the map
   * @throws Exception the exception
   */
  @DeleteMapping("/Acct/{id}")
  public Map<String, Boolean> deleteAcct(@PathVariable(value = "id") Long AcctId) throws Exception {
    Acct Acct =
        AcctRepository
            .findById(AcctId)
            .orElseThrow(() -> new ResourceNotFoundException("Acct not found on :: " + AcctId));

    AcctRepository.delete(Acct);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
