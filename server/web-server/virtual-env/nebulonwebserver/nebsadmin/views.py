from django.shortcuts import render
from django.contrib.auth import authenticate, login as django_login, logout

# LogIn view
# request is an HttpRequest object 
#   see: https://docs.djangoproject.com/en/2.1/ref/request-response/#django.http.HttpRequest
def login(request):

    # User sent a username and a password
    if request.method == 'POST':

        # Context for the template
        context = {
            'authenticated': False,
        }

        username = request.POST['userinput']
        password = request.POST['passwordinput']
        user = authenticate(username = username, password = password)

        if user is not None:
            # If it is a superuser and is active
            if user.is_superuser and user.is_active:
                
                django_login(request, user)
                return render(request, 'nebsadmin/index.html')

            else:

                return render(request, 'nebsadmin/login.html', context)
                
        else:

            return render(request, 'nebsadmin/login.html', context)
            
    else: # GET request
        return render(request, 'nebsadmin/login.html')
