from django.shortcuts import render
from django.contrib.auth import authenticate, login as django_login, logout

# LogIn view
# request is an HttpRequest object 
#   see: https://docs.djangoproject.com/en/2.1/ref/request-response/#django.http.HttpRequest
def login(request):

    # User sent a username and a password
    if request.method == 'POST':
        username = request.POST['userinput']
        password = request.POST['passwordinput']
        user = authenticate(username = username, password = password)

        if user is not None:
            if user.is_active:
                django_login(request, user)
                return render(request, 'nebsadmin/pprincipal.html')
            else:
                print ("Your account is not active")
                return render(request, 'nebsadmin/login.html')
            
        else:
            print("Wrong username or password. Try again!")
            return render(request, 'nebsadmin/login.html')

        #return render(request, 'nebsadmin/pprincipal.html')
            
    else: # GET request
        return render(request, 'nebsadmin/login.html')
