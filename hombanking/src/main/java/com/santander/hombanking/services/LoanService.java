package com.santander.hombanking.services;

import com.santander.hombanking.dtos.LoanApplicationDTO;
import com.santander.hombanking.models.Loan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoanService {

    @Transactional
    public ResponseEntity<Object> addLoan(LoanApplicationDTO loanApplicationDTO){
//    Debe recibir un objeto de solicitud de crédito con los datos del préstamo


//    Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
//    Verificar que el préstamo exista
//    Verificar que el monto solicitado no exceda el monto máximo del préstamo
//    Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
//    Verificar que la cuenta de destino exista
//    Verificar que la cuenta de destino pertenezca al cliente autenticado
//    Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
//    Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo) con la descripción concatenando el nombre del préstamo y la frase “loan approved”
//    Se debe actualizar la cuenta de destino sumando el monto solicitado.





        return new ResponseEntity<>("Delivered Loan", HttpStatus.ACCEPTED);
    }
}
