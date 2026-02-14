# Estrutura do Projeto
Abaixo está a organização das pastas e arquivos do sistema:

     sistema-cadastro
    ┣  formulario.txt      <-- (Banco de dados em txt)
    ┗  src
        ┗  devkaua
            ┗  projeto
                ┃
                ┣ domain (Modelos)
                ┃    ┣  Pet            <-- (Objeto principal)
                ┃    ┣  Endereco       
                ┃    ┣  Sexo           
                ┃    ┗  TipoAnimal     
                ┃
                ┣ service (Regras de Negócio)
                ┃    ┗  AlteracoesPet   <-- (Lê arquivo, valida dados e CRUD geral)
                ┃
                ┗ Main                 <-- (Inicia o sistema)