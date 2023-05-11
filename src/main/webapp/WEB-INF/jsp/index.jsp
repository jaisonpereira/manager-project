<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>System manager</title>
  </head>
  <body>
  <body class="bg-light">
    <div class="container">
      <div class="py-5 text-center">
        <img class="d-block mx-auto mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
        <h2>Manager Projects - Test System</h2>
        <p class="lead">Sistema escrito em Java 11 utilizando as seguintes tecnologias</p>
        <ul class="list-group">
          <li class="list-group-item">Spring boot</li>
          <li class="list-group-item">flyway</li>
          <li class="list-group-item">Docker Container</li>
          <li class="list-group-item">Spring Data</li>
          <li class="list-group-item">Bootstrap</li>
          <li class="list-group-item">expondo o cadastro de membros atraves endpoint localhost:8080/membro</li>
          <li class="list-group-item">TESTES JUNIT 5</li>
          <li class="list-group-item">Maven</li>

        </ul>
      </div>
  <c:if test="${not empty erro}">
    <div class="row">
      <div class="alert alert-danger" role="alert">
        ${erro.mensagem}
      </div>
    </div>
  </c:if> 
      <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-muted">Projetos Cadastrados</span>
            <span class="badge badge-secondary badge-pill">${projetos.size()}</span>
          </h4>
          <ul class="list-group mb-3">
            <c:forEach items="${projetos}" var="projeto">
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <h6 class="my-0">${projeto.nome}</h6>
                <small class="text-muted">${projeto.descricao}</small>
              </div>
              <div class="btn-group btn-group-sm" role="group" aria-label="control">
                <form action="/projeto/update/${projeto.id}" method="post">
                  <button type="submit" class="btn btn-warning">Alterar</button>
                </form>
                <form action="/projeto/delete/${projeto.id}" method="post">
                  <button type="submit" class="btn btn-danger">Excluir</button>
                </form>
              </div>
            </li>
          </c:forEach>
          </ul>
        </div>
        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">Cadastro Projeto</h4>
          <c:url var="add_project_url" value="/"/>
          <form:form action="${add_project_url}" class="needs-validation" method="post" modelAttribute="projetoDto">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="nome">Nome</label>
                <form:input type="text" path="nome" class="form-control" required="required"  />
                <form:input type="hidden" path="id"  />
                <div class="invalid-feedback">
                  Valid  name is required="".
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="dataInicio">Data de ínicio</label>
                <form:input type="date" path="dataInicio" class="form-control"   />
              </div>
            </div>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="orcamento">Orçamento Total</label>
                <form:input type="number"  step="0.01" min="0" path="orcamento" class="form-control"   />
              </div>
            </div>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="dataPrevisaoFim">Data Previsão Fim</label>
                <form:input type="date" path="dataPrevisaoFim" class="form-control"   />
              </div>
              <div class="col-md-6 mb-3">
                <label for="dataFim">Data Fim Real</label>
                <form:input type="date" path="dataFim" class="form-control"   />
              </div>
            </div>
            <div class="row">
              <div class="col-md-12 mb-12">
                <label for="descricao">Descrição</label>
                <form:textarea type="text" path="descricao" class="form-control"   />
              </div>
            </div>
            <div class="row">
              <div class="col-md-5 mb-3">
                <label for="status">Status</label>
                <form:select  path="status" class="custom-select d-block w-100" >
                  <form:option value="">Selecione...</form:option>
                  <form:option value="EM_ANALISE">em análise</form:option>
                  <form:option value=" ANALISE_REALIZADA">análise realizada</form:option>
                  <form:option value="ANALISE_APROVADA">análise aprovada</form:option>
                  <form:option value="INICIADO">iniciado</form:option>
                  <form:option value="PLANEJADO">planejado</form:option>
                  <form:option value="EM_ANDAMENTO">em andamento</form:option>
                  <form:option value="ENCERRADO">encerrado</form:option>
                  <form:option value="CANCELADO">cancelado</form:option>
                </form:select>
              </div>
              <div class="col-md-4 mb-3">
                <label for="state"  >Gerente</label>
                <form:select   class="custom-select d-block w-100" path="IdGerente" required="">
                  <form:option value="">Selecione...</form:option>
                  <c:forEach var="pessoa" items="${pessoas}">
                    <form:option value="${pessoa.id}" label="${pessoa.nome}"/>
                  </c:forEach> 
                </form:select>
                <div class="invalid-feedback">
                  Please provide a valid Gerente.
                </div>
              </div>
              <div class="col-md-3 mb-3">
                <label for="state">Risco</label>
                <form:select  class="custom-select d-block w-100" path="risco" >
                  <form:option value="">Selecione...</form:option>
                  <form:option value="BAIXO_RISCO">baixo risco</form:option>
                  <form:option value="MEDIO_RISCO">médio risco</form:option>
                  <form:option value="ALTO_RISCO">alto risco</form:option>
                </form:select>

              </div>
            </div>

            <button class="btn btn-primary btn-lg btn-block" type="submit" value="submit" >Salvar Projeto</button>
          </form:form>
        </div>
      </div>

      <footer class="my-5 pt-5 text-muted text-center text-small">
        <p class="mb-1">&copy; 2023 Test manager project Jaison Pereira</p>
      </footer>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </body>
</html>
