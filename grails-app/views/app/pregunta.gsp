<!DOCTYPE html>
<html>
    <head>
        <title> Listado de preguntas</title>
    </head>
    <body>
        <h3>${question.get('text').getAsString()}</h3>
        <g:form action='answer' id='${params.id}'>
            <textarea id='txtAnswer' name='answer' maxlength='2000' required cols='100' rows='8'></textarea><br>
            Quedan <span id='spanRemaining'></span> caracteres.<br>
            <button type='submit'>Responder</button>
        </g:form>
        <script>
            var txtAnswer = document.getElementById('txtAnswer');
            var spanRemaining = document.getElementById('spanRemaining');
            spanRemaining.innerHTML = txtAnswer.maxLength-txtAnswer.value.length;

            txtAnswer.addEventListener('keypress', function(){
                spanRemaining.innerHTML = txtAnswer.maxLength-txtAnswer.value.length;
            });
        </script>
    </body>
</html>
