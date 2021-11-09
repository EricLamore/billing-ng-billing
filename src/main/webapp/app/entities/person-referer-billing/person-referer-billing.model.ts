export interface IPersonRefererBilling {
  id?: string;
  firstname?: string | null;
  lastname?: string | null;
  email?: string | null;
  job?: string | null;
  phoneNumber?: string | null;
  mobile?: string | null;
  fax?: string | null;
  description?: string | null;
}

export class PersonRefererBilling implements IPersonRefererBilling {
  constructor(
    public id?: string,
    public firstname?: string | null,
    public lastname?: string | null,
    public email?: string | null,
    public job?: string | null,
    public phoneNumber?: string | null,
    public mobile?: string | null,
    public fax?: string | null,
    public description?: string | null
  ) {}
}

export function getPersonRefererBillingIdentifier(personReferer: IPersonRefererBilling): string | undefined {
  return personReferer.id;
}
