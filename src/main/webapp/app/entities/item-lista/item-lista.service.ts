import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItemLista } from 'app/shared/model/item-lista.model';

type EntityResponseType = HttpResponse<IItemLista>;
type EntityArrayResponseType = HttpResponse<IItemLista[]>;

@Injectable({ providedIn: 'root' })
export class ItemListaService {
  public resourceUrl = SERVER_API_URL + 'api/item-listas';

  constructor(protected http: HttpClient) {}

  create(itemLista: IItemLista): Observable<EntityResponseType> {
    return this.http.post<IItemLista>(this.resourceUrl, itemLista, { observe: 'response' });
  }

  update(itemLista: IItemLista): Observable<EntityResponseType> {
    return this.http.put<IItemLista>(this.resourceUrl, itemLista, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemLista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemLista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
