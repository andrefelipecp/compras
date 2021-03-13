import { IItemLista } from 'app/shared/model/item-lista.model';

export interface ILista {
  id?: number;
  nome?: string;
  itemListas?: IItemLista[];
}

export class Lista implements ILista {
  constructor(public id?: number, public nome?: string, public itemListas?: IItemLista[]) {}
}
