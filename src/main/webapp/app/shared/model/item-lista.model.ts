import { ILista } from 'app/shared/model/lista.model';

export interface IItemLista {
  id?: number;
  nome?: string;
  preco?: number;
  lista?: ILista;
}

export class ItemLista implements IItemLista {
  constructor(public id?: number, public nome?: string, public preco?: number, public lista?: ILista) {}
}
