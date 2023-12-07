package main

import (
	"fmt"
	"net/http"
	"time"
	"io/ioutil"
	"bytes"
)

func get(url string) string {
    ch := make(chan string, 3)

    go getFromUrl(url, ch)
    go getFromUrl(url, ch)
    go getFromUrl(url, ch)

    time.Sleep(1 * time.Second)

    select {
        case response := <-ch:
            return response
	default:
            return "Erro"
    }
}

func getFromUrl(url string, out chan string) {
	response, err := http.Get(url)
	if err != nil {out <- "Erro durante o get."}

	body, readErr := ioutil.ReadAll(response.Body)
	response.Body.Close()

	if readErr != nil {out <- "Erro durante leitura"}
	sb := string(body)
	out <- sb
}

func postPut(url string, jsonData []byte) {
        resp, err := http.Post(url, "application/json", bytes.NewBuffer(jsonData))
        if err != nil {
		fmt.Println("Ocorreu um erro durante o método post:", err)
        }

        defer resp.Body.Close()
        fmt.Println("Status da resposta:", resp.Status)
}

func main() {
	usuarios := "http://localhost:8080/v1/usuarios"
	publicacoes := "http://localhost:8080/v1/publicacoes"
	comentarios := "http://localhost:8080/v1/comentarios"

	jsonUserData := []byte(`{"apelido": "LeviJunior","email": "levi.pereira.junior@ccc.ufcg.edu.br","codigoAcesso": 12345, "idAuth": 1111, "loginType": "GitHub"}`)
	fmt.Println("Criando o primeiro usuário!")
	postPut(usuarios, jsonUserData)

	jsonUserData2 := []byte(`{"apelido": "AnaClara","email": "anaclara@gmail.com","codigoAcesso": 12345, "idAuth": 1111, "loginType": "GitHub"}`)
        fmt.Println("Criando o segundo usuário!")
        postPut(usuarios, jsonUserData2)

	fmt.Println("\nRecuperando todos usuários criados:")
	responseCriacaoUsuario := get(usuarios)
	fmt.Println("Resposta:", responseCriacaoUsuario)

	fmt.Println("\nCriando uma publicação para o primeiro usuário criado!")

	criarPublicacoes :=  "http://localhost:8080/v1/publicacoes/publicacao?id=2"
	jsonPostData := []byte(`{"publicacao": "Bom dia!","date": "2023-10-24T12:00:00Z","codigoAcesso": 12345, "categoria": "amizade"}`)
	postPut(criarPublicacoes, jsonPostData)

        fmt.Println("\nRecuperando publicacões seguidas do usuário criado:")

        responseSeguindoUsuario := get(publicacoes + "/seguindo/1")
        fmt.Println("Resposta:", responseSeguindoUsuario)

	fmt.Println("Criando um comentário para a publicação criada.")

	criarComentario := "http://localhost:8080/v1/comentarios/1/usuario?idUsuario=2"
	jsonPostComentario := []byte(`{"comentario": "Bom dia, amiga!","codigoAcesso": 12345, "idUsuario": 2, "timestamp": "2023-10-24T12:00:00Z"}`)
        postPut(criarComentario, jsonPostComentario)

	gostarComentario := "http://localhost:8080/v1/comentarios/gostar/publicacao/1/comentario?id=1"
	jsonGostarComentario := []byte(`{"idUsuario": 1,"codigoAcesso": 12345}`)
        fmt.Println("\nGostando de uma publicação")
        postPut(gostarComentario, jsonGostarComentario)

	fmt.Println("\nRecuperando todos usuários criados:")
	responseCriacaoUsuario2 := get(usuarios)
        fmt.Println("Resposta:", responseCriacaoUsuario2)

	fmt.Println("\n\nRecuperando todas as publicações criadas")
	responsePublicacoesCriadas := get(publicacoes)
	fmt.Println("Resposta:", responsePublicacoesCriadas)

	fmt.Println("\n\nRecuperando todas os comentarios da pulicacao feita")
        responseComentariosCriados := get(comentarios + "/publicacao/1 ")
        fmt.Println("Resposta:", responseComentariosCriados)

	fmt.Println("\nRecuperando publicacões seguidas do usuário criado:")
        responseSeguindoUsuario2 := get(publicacoes + "/seguindo/1")
        fmt.Println("Resposta:", responseSeguindoUsuario2)
}

