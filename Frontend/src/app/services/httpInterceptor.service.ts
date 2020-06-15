import { HttpHeaders, HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';



@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {


                console.log("USAAAOOO JE NEMANJA");

        const jwt = localStorage.getItem("jwt");

        //const xsrfToken=localStorage.getItem("xsrfToken");

        console.log("Tokencic je ovaj=="+jwt);


      const xsrfToken = this.getCookie('XSRF-TOKEN');



      

        if (jwt) {
            const cloned = req.clone({

                headers: req.headers.set("Authorization",
                      jwt)
            });
            console.log("Pogodi prvi if!")

            return next.handle(cloned);
        }
        else if(xsrfToken){
            const cloned = req.clone({
                headers: req.headers
                      //.set('X-XSRF-TOKEN', xsrfToken)
            });
            console.log("Pogodi DRUGI IF");
            return next.handle(cloned);
        }
        else {
            return next.handle(req);
        }

    }


 getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}


}