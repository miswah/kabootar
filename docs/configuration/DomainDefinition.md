## 1. ConfigurationRevision

The central object in configuration service design. Think od this as a particular version of configuration at that point in time

Revision 1 or Revision 2 doesn't replace each other we store both and use based on user preference


Object Definition
```text
id
revisionNumber //Need to be always increasing
status
basedOnRevisionId
schemaVersion  //in case we change schema of configurationRevision in future
checksum //Quick look up
lockVersion
createdBy
createdAt
updatedBy
updatedAt
publishedBy
publishedAt
```

## 2. ConfigurationSnapshot

The complete configuration that runtime consumers are allowed to consume.

Every new publishing creates a new configurationSnapshot

```text
id
schemaVersion
revisionNumber
checksum
regions
services
serviceInstances
routes
```

## 3. ValidationResult

we need a structured validation report in case of failure

```text
id
revisionNumber
status
validatedAt
violations[] : ValidationViolation
```

## 3.2 ValidationViolation

Each problem should be indepedently represented 

```json
{
  "code": "UNKNOWN_REGION",
  "path": "services.demo-service.instances[0].region",
  "message": "Region does not exist"
}
```
Violation Schema

```text
code
message
path
severity // can be optional we will refine this further when working on validation story TODO
```

## 4. PublishResult

```text
revisionId
revisionNumber
publishedAt
publishedBy
checksum
lockVersion
```
The most important thing here is that we need to make sure that each publish result map to a revsion number

## 5. AuditEvent

Events
1. DRAFT_CREATED
2. DRAFT_UPDATED
3. VALIDATION_RUN
4. DRAFT_DISCARDED
5. REVISION_PUBLISHED
6. REVISION_RESTORED

Schema
```text
    revisionNumber
    event
    createdBy
    createdAt
    updatedBy
    updatedAt
    publishedBy
    publishedAt
```

## Some Rules

1. As soon as the snapshot is published it becomes immutable CRUD api should not be able to modify it
2. Each draft should have a lockversion to manage concurrent edits
3. Any edits to the snapshot can only be done by making a new draft from it