from django.shortcuts import render
from django.http import JsonResponse
from .utils import generate_response



# http://localhost:8000/chatbot/?message={질문}
def chat(request):
    if request.method == 'GET':
        message = request.GET.get('message', '')
        
        response = generate_response(message)
        
        return JsonResponse({
            'response': response,
        })