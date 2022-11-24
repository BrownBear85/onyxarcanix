package com.brownbear85.onyxarcanix.entity;

import com.brownbear85.onyxarcanix.init.EntityTypeInit;
import com.brownbear85.onyxarcanix.particle.MagicParticleOptions;
import com.brownbear85.onyxarcanix.spell.Spell;
import com.brownbear85.onyxarcanix.init.SpellInit;
import com.brownbear85.onyxarcanix.spell.spells.ProjectileSpell;
import com.mojang.math.Vector3f;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpellEntity extends Projectile {
    private static final EntityDataAccessor<Float> DATA_RED = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_GREEN = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_BLUE = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<String> DATA_SPELL = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.STRING);

    public SpellEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static void shootFromEntity(Level level, Entity shooter, Vector3f color, String spell) {
        SpellEntity spellEntity = new SpellEntity(EntityTypeInit.SPELL.get(), level);
        spellEntity.setColor(color);
        spellEntity.setSpell(spell);
        spellEntity.setPos(shooter.getPosition(0).add(0.0, shooter.getEyeHeight(), 0.0));
        spellEntity.setOwner(shooter);
        spellEntity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 0.35F, 0F);
        level.addFreshEntity(spellEntity);
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }
        if (this.level instanceof ClientLevel) {
            level.addParticle(MagicParticleOptions.SPELL_SHOT(getColor()), getX(), getY() + 0.05, getZ(), 0, 0, 0);
        }
        Vec3 velocity = this.getDeltaMovement();
        double x = this.getX() + velocity.x;
        double y = this.getY() + velocity.y;
        double z = this.getZ() + velocity.z;
        this.lerpTo(x, y, z, getXRot(), getYRot(), 1, false);
    }

    @Override
    protected void onHit(HitResult pResult) {
        Spell spell = SpellInit.get(this.getSpell());
        if (spell instanceof ProjectileSpell) {
            HitResult result = ProjectileUtil.getHitResult(this, this::canHitEntity);
            ((ProjectileSpell) spell).onProjectileHit(level, this.getOwner(), this, result);
        }
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
        }
        this.discard();
    }

    /* color */

    public void setColor(Vector3f color) {
        this.getEntityData().set(DATA_RED, color.x());
        this.getEntityData().set(DATA_GREEN, color.y());
        this.getEntityData().set(DATA_BLUE, color.z());
    }

    public Vector3f getColor() {
        return new Vector3f(this.getEntityData().get(DATA_RED), this.getEntityData().get(DATA_GREEN), this.getEntityData().get(DATA_BLUE));
    }

    /* spell */

    public void setSpell(String spell) {
        this.getEntityData().set(DATA_SPELL, spell);
    }

    public String getSpell() {
        return this.getEntityData().get(DATA_SPELL);
    }

    /* nbt */

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RED, 0.0F);
        this.getEntityData().define(DATA_GREEN, 0.0F);
        this.getEntityData().define(DATA_BLUE, 0.0F);
        this.getEntityData().define(DATA_SPELL, "none");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ListTag list = new ListTag();
        list.add(FloatTag.valueOf(this.getEntityData().get(DATA_RED)));
        list.add(FloatTag.valueOf(this.getEntityData().get(DATA_GREEN)));
        list.add(FloatTag.valueOf(this.getEntityData().get(DATA_BLUE)));
        pCompound.put("color", list);
        pCompound.putString("spell", this.getSpell());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ListTag list = pCompound.getList("color", 5);
        this.setColor(new Vector3f(list.getFloat(0), list.getFloat(1), list.getFloat(2)));
        this.setSpell(pCompound.getString("spell"));
    }
}
