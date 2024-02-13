## Spring Security (Lista de ativos da bolsa)

Fluxo para criação do banco de dados:

`docker run --name spring-fiap -e POSTGRES_PASSWORD=102030 -d -p 5432:5432 postgres`

Agora para rodar o projeto execute: `mvn clean install` para instalar as dependências e `mvn spring-boot:run` para subir a aplicação.

POM


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-config</artifactId>
			<version>6.1.5</version> 
		</dependency>

		<dependency>
			<groupId>com.auth0</groupId>
			<artifactId>java-jwt</artifactId>
			<version>4.4.0</version>
		</dependency>

Cria a pasta Security mas agora com 3 classes

1) TokenService
   para Gerar o Token no momento do Login
   Validar o Token para acessar as regras de autorização

   @Value("${api.security.token.secret}") em um atributo string para gerar o Token
   iss: origem do token

Os payloads consistem em objetos JSON que contêm as reivindicações
(claims) em que trabalhamos com os "pedidos", que representam a carga de dados
ou informações enviadas.
Existem três categorias de reivindicações em payloads: reivindicações
reservadas, reivindicações públicas e reivindicações privadas.

Reivindicações Reservadas: são atributos que não são estritamente
obrigatórios, mas altamente recomendados, visto que são amplamente utilizados por
protocolos de segurança em APIs. Alguns exemplos incluem:
"iss" (Issuer): indica a origem do token.
"iat" (Issued At): representa o carimbo de data e hora no qual o token foi gerado.
"exp" (Expiration): indica o carimbo de data e hora no qual o token expirará.
"sub" (Subject): identifica a entidade à qual o token pertence, geralmente o ID
do usuário.

Reivindicações Públicas: são atributos que definem o uso do JWT e fornecem
informações úteis para a aplicação.

São públicas, o que significa que estão disponíveis para quem decodifica o
token. Por exemplo: podem conter informações sobre o tipo de token ou o escopo de
acesso.

Reivindicações Privadas: são atributos definidos especificamente para
compartilhar informações entre aplicações.
Essas informações são acordadas entre as partes envolvidas e não são
especificadas ou padronizadas por nenhum protocolo. As reivindicações privadas são
personalizadas e podem ser usadas para transmitir dados específicos que sejam
relevantes para o contexto da aplicação.
Lembrando que as reivindicações reservadas são recomendadas para garantir
uma interoperabilidade adequada com outros sistemas, ao passo que as
reivindicações públicas e privadas oferecem flexibilidade para incluir informações
adicionais ou personalizadas nos tokens JWT, conforme necessário para as
necessidades específicas da aplicação.


2) criamos um 2 classe que vai injetar o Token acima e o banco dados, além de fazer a herança da classe: OncePerRequestFilter
   Que diferente de quando temos a anotação @EnableWebSecurity que já injeta no spring boot o componenete para ver no metodo bean as regras de segurança, ela usa o que fazemos antes com as servlets
   implementa um filtro para capturar todas as requisoções, e neste exeplo validamos apenas se o token esta válido caso venha preechida e com a regra da criptografia ja sabe o a regra do usuário.

que será validado depois na 3 classe abaixo com as regras definidaas mas podemos tbm usar um xml para esta configuração de roles vs endpoints

usa a classe: UserDetails,UsernamePasswordAuthenticationToken,SecurityContextHolder
a nossa classe user tem que implementar a interface UserDetails
que implementa diversos metodos, sendo um dos principais : getAuthorities para saber o tipo de role.

metodo: doFilterInternal
classes: HttpServletRequest,HttpServletResponse,FilterChain
UsernamePasswordAuthenticationToken




3)
classe: HttpSecurity
anotação: EnableWebSecurity

esta classe o metodo bean dela com as regras de segurança
e o que spring security assume as regras de autorização de acesso aos endpoints...