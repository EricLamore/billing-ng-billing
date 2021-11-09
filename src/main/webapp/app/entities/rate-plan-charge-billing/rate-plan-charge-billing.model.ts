export interface IRatePlanChargeBilling {
  id?: string;
  step?: number | null;
  unitPrice?: number | null;
  discount?: number | null;
}

export class RatePlanChargeBilling implements IRatePlanChargeBilling {
  constructor(public id?: string, public step?: number | null, public unitPrice?: number | null, public discount?: number | null) {}
}

export function getRatePlanChargeBillingIdentifier(ratePlanCharge: IRatePlanChargeBilling): string | undefined {
  return ratePlanCharge.id;
}
