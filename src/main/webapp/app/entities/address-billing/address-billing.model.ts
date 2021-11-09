export interface IAddressBilling {
  id?: string;
  recipient?: string | null;
  postOfficeBox?: string | null;
  complement?: string | null;
  street?: string | null;
  postcode?: string | null;
  city?: string | null;
  country?: string | null;
}

export class AddressBilling implements IAddressBilling {
  constructor(
    public id?: string,
    public recipient?: string | null,
    public postOfficeBox?: string | null,
    public complement?: string | null,
    public street?: string | null,
    public postcode?: string | null,
    public city?: string | null,
    public country?: string | null
  ) {}
}

export function getAddressBillingIdentifier(address: IAddressBilling): string | undefined {
  return address.id;
}
