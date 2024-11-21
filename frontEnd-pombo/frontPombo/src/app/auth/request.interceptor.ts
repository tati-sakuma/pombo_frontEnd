import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { Inject } from "@angular/core";
import { error } from "console";
import { catchError, throwError } from "rxjs";

  export const RequestInterceptor: HttpInterceptorFn = (req, next) => {

    const tokenUsuarioAutenticado = localStorage.getItem('tokenUsuarioAutenticado');
    let authReq = req;

    if (tokenUsuarioAutenticado) {
      authReq = req.clone({
          setHeaders: { Authorization: `Bearer ${tokenUsuarioAutenticado}` }
      });
    }

    return next (authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 || error.status === 403) {

        }
        return throwError(error);
      })
    );
  }
