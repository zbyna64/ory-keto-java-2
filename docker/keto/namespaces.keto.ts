import {Context, SubjectSet, Namespace} from "@ory/keto-namespace-types"

// class User implements Namespace {}
//
// class Group implements Namespace{
//     related: {
//         members: User[]
//         managers: User[]
//     }
//
//     permits = {
//         isManager: (ctx: Context): boolean => this.related.managers.includes(ctx.subject)
//     }
// }
//
// class Report implements Namespace {
//     related: {
//         viewers: SubjectSet<Group, 'managers'>[]
//         editors: User[]
//         parents: Report[]
//         owners: User[]
//     }
//
//     permits = {
//         view: (ctx: Context): boolean =>
//             this.related.viewers.includes(ctx.subject) ||
//             this.related.parents.traverse((p) => p.permits.view(ctx)) ||
//             this.permits.edit(ctx) ||
//             this.related.viewers.traverse(v =>  v.related.managers.includes(ctx.subject)),
//         edit: (ctx: Context) =>
//             this.related.owners.includes(ctx.subject) ||
//             this.related.editors.includes(ctx.subject) ||
//             this.related.parents.traverse((p) => p.permits.edit(ctx)),
//     }
// }



class User implements Namespace {
    related: {
        manager: User[]
    }

    permits = {
        isManager: (ctx: Context): boolean => this.related.manager.includes(ctx.subject)
    }
}

class Organization implements Namespace {
    related: {
        viewers: User[]
        editors: User[]
        parents: Organization[]
        owners: User[]
    }

    permits = {
        view: (ctx: Context): boolean =>
            this.related.viewers.includes(ctx.subject) ||
            this.related.parents.traverse((p) => p.permits.view(ctx)) ||
            this.permits.edit(ctx) ||
            this.related.owners.traverse((owner) => owner.related.manager.includes(ctx.subject)) ||
            this.related.owners.traverse((owner) => owner.permits.isManager(ctx)),
        edit: (ctx: Context) =>
            this.related.owners.includes(ctx.subject) ||
            this.related.editors.includes(ctx.subject) ||
            this.related.parents.traverse((p) => p.permits.edit(ctx)),
    }
}





