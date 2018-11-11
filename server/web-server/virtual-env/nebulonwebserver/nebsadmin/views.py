from django.shortcuts import render
from django.contrib.auth import authenticate, login, logout

# LogIn view
def login(request):

    if request.method == 'POST':
        username = request.POST['username']
        password = request.POST['password']
        user = authenticate(username = username, password = password)

        if user is not None:
            if user.is_active:
                login(request, user)
                return render(request, 'nebsadmin/index.html')
            else:
                print "Your account is not active"
                return render(request, 'nebsadmin/login.html')
            
        else:
            print "Wrong user or password. Try again!"
            return render(request, 'nebsadmin/login.html')
            
    else: # GET request
        return render(request, 'nebsadmin/login.html')
        
    
