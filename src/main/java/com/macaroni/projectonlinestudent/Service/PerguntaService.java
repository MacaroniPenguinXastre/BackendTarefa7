package com.macaroni.projectonlinestudent.Service;

import com.macaroni.projectonlinestudent.Repository.PerguntaRepository;
import com.macaroni.projectonlinestudent.model.CargoUser;
import com.macaroni.projectonlinestudent.model.Pergunta;
import com.macaroni.projectonlinestudent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class PerguntaService {
    @Autowired
    PerguntaRepository perguntaRepository;

    public HttpStatusCode criarPergunta(Pergunta pergunta, User user){
        if(user.getCargo().equals(CargoUser.ALUNO) || pergunta == null){
            return HttpStatusCode.valueOf(403);
        }

        pergunta.setMentor(user);
        perguntaRepository.saveAndFlush(pergunta);

        System.out.println("Pergunta criada com sucesso");
        return HttpStatusCode.valueOf(200);
    }

}
