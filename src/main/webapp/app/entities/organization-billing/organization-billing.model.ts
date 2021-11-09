import * as dayjs from 'dayjs';

export interface IOrganizationBilling {
  id?: string;
  addr?: string | null;
  city?: string | null;
  country?: string | null;
  name?: string | null;
  registerDate?: dayjs.Dayjs | null;
  status?: number | null;
  zipCode?: string | null;
  individual?: boolean | null;
  vatNumber?: string | null;
  ipRanges?: string | null;
}

export class OrganizationBilling implements IOrganizationBilling {
  constructor(
    public id?: string,
    public addr?: string | null,
    public city?: string | null,
    public country?: string | null,
    public name?: string | null,
    public registerDate?: dayjs.Dayjs | null,
    public status?: number | null,
    public zipCode?: string | null,
    public individual?: boolean | null,
    public vatNumber?: string | null,
    public ipRanges?: string | null
  ) {
    this.individual = this.individual ?? false;
  }
}

export function getOrganizationBillingIdentifier(organization: IOrganizationBilling): string | undefined {
  return organization.id;
}
