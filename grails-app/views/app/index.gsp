<!DOCTYPE html>
<html>
    <head>
        <title> Listado de preguntas</title>
        <style type="text/css">
            table{
                border: 1px solid gray;
                background-color: lightgray;
                width: 100%;
            }
            thead{
                background-color: gray;
                color: white;
            }
        </style>
    </head>
    <body>
        <h1>Preguntas sin responder</h1>
        <g:if test="${total>0}">
            <table>
                <thead>
                    <tr>
                        <th>Pregunta</th>
                        <th>Item</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td>Total: ${total}</th>
                    </tr>
                </tfoot>
                <tbody>
                    <g:each in="${questions}">
                        <tr>
                            <td><g:link action="pregunta" id="${it.get('id').getAsString()}">
                                ${it.get('text').getAsString()}
                            </g:link></td>

                            <td><a href="${it.get('item_url').getAsString()}">
                                ${it.get('item_title').getAsString()}
                            </a></td>
                        </tr>
                    </g:each>
                <tbody>
            </table>
            <g:if test="${offset != 0}">
                <g:link action='index' params="${['offset': offset>50?offset-50:0]}"><button>Anterior pagina</button></g:link>
            </g:if>
            <g:if test="${total > offset + 50}">
                <g:link  action='index' params="${['offset': offset+50]}"><button>Proxima pagina</button></g:link>
            </g:if>
        </g:if>
        <g:else>
            <p> No quedan preguntas sin responder! </p>
        </g:else>
    </body>
</html>
