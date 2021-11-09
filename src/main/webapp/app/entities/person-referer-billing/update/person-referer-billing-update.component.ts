import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPersonRefererBilling, PersonRefererBilling } from '../person-referer-billing.model';
import { PersonRefererBillingService } from '../service/person-referer-billing.service';

@Component({
  selector: 'jhi-person-referer-billing-update',
  templateUrl: './person-referer-billing-update.component.html',
})
export class PersonRefererBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstname: [],
    lastname: [],
    email: [],
    job: [],
    phoneNumber: [],
    mobile: [],
    fax: [],
    description: [],
  });

  constructor(
    protected personRefererService: PersonRefererBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personReferer }) => {
      this.updateForm(personReferer);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personReferer = this.createFromForm();
    if (personReferer.id !== undefined) {
      this.subscribeToSaveResponse(this.personRefererService.update(personReferer));
    } else {
      this.subscribeToSaveResponse(this.personRefererService.create(personReferer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonRefererBilling>>): void {
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

  protected updateForm(personReferer: IPersonRefererBilling): void {
    this.editForm.patchValue({
      id: personReferer.id,
      firstname: personReferer.firstname,
      lastname: personReferer.lastname,
      email: personReferer.email,
      job: personReferer.job,
      phoneNumber: personReferer.phoneNumber,
      mobile: personReferer.mobile,
      fax: personReferer.fax,
      description: personReferer.description,
    });
  }

  protected createFromForm(): IPersonRefererBilling {
    return {
      ...new PersonRefererBilling(),
      id: this.editForm.get(['id'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      email: this.editForm.get(['email'])!.value,
      job: this.editForm.get(['job'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
