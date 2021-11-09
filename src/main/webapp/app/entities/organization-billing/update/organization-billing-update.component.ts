import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrganizationBilling, OrganizationBilling } from '../organization-billing.model';
import { OrganizationBillingService } from '../service/organization-billing.service';

@Component({
  selector: 'jhi-organization-billing-update',
  templateUrl: './organization-billing-update.component.html',
})
export class OrganizationBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    addr: [],
    city: [],
    country: [],
    name: [],
    registerDate: [],
    status: [],
    zipCode: [],
    individual: [],
    vatNumber: [],
    ipRanges: [],
  });

  constructor(
    protected organizationService: OrganizationBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      this.updateForm(organization);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.createFromForm();
    if (organization.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationBilling>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(organization: IOrganizationBilling): void {
    this.editForm.patchValue({
      id: organization.id,
      addr: organization.addr,
      city: organization.city,
      country: organization.country,
      name: organization.name,
      registerDate: organization.registerDate,
      status: organization.status,
      zipCode: organization.zipCode,
      individual: organization.individual,
      vatNumber: organization.vatNumber,
      ipRanges: organization.ipRanges,
    });
  }

  protected createFromForm(): IOrganizationBilling {
    return {
      ...new OrganizationBilling(),
      id: this.editForm.get(['id'])!.value,
      addr: this.editForm.get(['addr'])!.value,
      city: this.editForm.get(['city'])!.value,
      country: this.editForm.get(['country'])!.value,
      name: this.editForm.get(['name'])!.value,
      registerDate: this.editForm.get(['registerDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      individual: this.editForm.get(['individual'])!.value,
      vatNumber: this.editForm.get(['vatNumber'])!.value,
      ipRanges: this.editForm.get(['ipRanges'])!.value,
    };
  }
}
